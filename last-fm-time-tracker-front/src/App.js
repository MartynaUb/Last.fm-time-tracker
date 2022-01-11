import './App.css';
import React from 'react';

const BASE_URL = 'http://localhost:8080/';

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {userName: '', initialized: false, loadingUser: false};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({userName: event.target.value});
    }

    async handleSubmit() {
        this.setState({loadingUser: true})
        const user = await fetch(`${BASE_URL}user?username=${this.state.userName}`, {method: 'POST'})
            .then(res => res.json());
        this.setState({user, initialized: true, loadingUser: false})
    }

    render() {
        if (this.state.loadingUser) {
            return <h2>Fetching user {this.state.userName}</h2>
        }
        if (this.state.initialized) {
            return <LastFmPage user={this.state.user}/>
        } else {
            return <div>Enter your last fm user name
                <form onSubmit={() => this.handleSubmit()}>
                    <label>
                        Username:
                        <input type="text" value={this.state.userName} onChange={this.handleChange}/>
                    </label>
                    <input type="submit" value="Submit"/>
                </form>
            </div>
        }
    }
}

class LastFmPage extends React.Component {

    constructor(props) {
        super(props);
        this.state = {trackPage: 0, artistPage: 0, tracks: [], artists: []};
    }

    handleNextTrackPage = async () => {
        const nextPage = this.state.trackPage + 1;
        const tracks = await fetch(`${BASE_URL}${this.props.user.id}/track?page=${nextPage}`).then(res => res.json())
        this.setState({tracks, trackPage: nextPage})
    }

    handlePreviousTrackPage = async () => {
        const previousPage = this.state.trackPage - 1;
        const tracks = await fetch(`${BASE_URL}${this.props.user.id}/track?page=${previousPage}`).then(res => res.json())
        this.setState({tracks, trackPage: previousPage})
    }

    handleNextArtistPage = async () => {
        const nextPage = this.state.artistPage + 1;
        const artists = await fetch(`${BASE_URL}${this.props.user.id}/artist?page=${nextPage}`).then(res => res.json())
        this.setState({artists, artistPage: nextPage})
    }

    handlePreviousArtistPage = async () => {
        const previousPage = this.state.artistPage - 1;
        const artists = await fetch(`${BASE_URL}${this.props.user.id}/artist?page=${previousPage}`).then(res => res.json())
        this.setState({artists, artistPage: previousPage})
    }

    async componentDidMount() {
        const [tracks, artists] = await Promise.all([
            fetch(`${BASE_URL}${this.props.user.id}/track`).then(res => res.json()),
            fetch(`${BASE_URL}${this.props.user.id}/artist`).then(res => res.json()),
        ]);

        this.setState({tracks, artists})
    }

    render() {

        return (
            <div>
                <TrackList tracks={this.state.tracks}/>
                {this.state.trackPage > 0 &&
                <button onClick={() => this.handlePreviousTrackPage()}>Previous page</button>
                }
                <button onClick={() => this.handleNextTrackPage()}>Next page</button>
                <ArtistList artists={this.state.artists}/>
                {this.state.artistPage > 0 &&
                <button onClick={() => this.handlePreviousArtistPage()}>Previous page</button>
                }
                <button onClick={() => this.handleNextArtistPage()}
                >Next page
                </button>
            </div>
        )
    }

}

class TrackList extends React.Component {
    render() {
        const tracks = this.props.tracks.map(track =>
            <Track key={track.songTitle + " " + track.artist} track={track}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Artist</th>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Play Count</th>
                    <th>Total Play Time</th>
                </tr>
                {tracks}
                </tbody>
            </table>
        )
    }
}

class Track extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.track.artist}</td>
                <td>{this.props.track.songTitle}</td>
                <td>{this.props.track.duration}</td>
                <td>{this.props.track.playCount}</td>
                <td>{this.props.track.totalPlayTime}</td>
            </tr>
        )
    }
}

class ArtistList extends React.Component {
    render() {
        const artists = this.props.artists.map(artist =>
            <Artist key={artist.artist + " " + artist.totalPlayTime} artist={artist}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Artist</th>
                    <th>Play Count</th>
                    <th>Total Play Time</th>
                </tr>
                {artists}
                </tbody>
            </table>
        )
    }
}

class Artist extends React.Component {
    render() {
        return (
            <tr>
                <td>{this.props.artist.artist}</td>
                <td>{this.props.artist.playCount}</td>
                <td>{this.props.artist.totalPlayTime}</td>
            </tr>
        )
    }
}

export default App;
