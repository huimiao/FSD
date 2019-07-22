import React, { Component, Fragment } from "react";
import { PlayerHeader, emitter } from "../common";
import { connect } from "react-redux";
import { changeVideoPlayingStatus } from "../PlayerList/store/actionCreators";
import { Repository } from "../../../services";
import {
  timeUpdated,
  durationUpdated
} from "../PlayerView/store/actionCreators";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import "./PlayerView.css";

class PlayerView extends Component {
  constructor(props) {
    super(props);
    this.repository =  new Repository();
    this.props.onRef(this);
    this.videoStatusTogger = this.videoStatusTogger.bind(this);
  }

  componentDidMount() {
    emitter.addListener("goToLastPlayed", lastPlayed => {
      this.refs.player.load();
      this.refs.player.currentTime = lastPlayed;
    })

    this.refs.player.addEventListener(
      "timeupdate",
      () => {
        let t = this.refs.player.currentTime;
        this.repository.saveToDB(this.props.selectedItemUrl, t);
        this.props.timeUpdated(t); 
      },
      false
    );

    this.refs.player.addEventListener(
      "durationchange",
      () => {
        this.props.durationUpdated(this.refs.player.duration);
      },
      false
    );

    this.refs.player.addEventListener(
      "ended",
      () => {
        this.refs.player.currentTime = 0;
        this.props.changeVideoPlayingStatus("pause");
      },
      false
    );
  }

  formatTime(time) {
    const h = Math.floor(time / 3600);
    const m = Math.floor((time % 3600) / 60);
    const s = Math.floor(time % 60);
    return (
      this.padding(h, 2) + ":" + this.padding(m, 2) + ":" + this.padding(s, 2)
    );
  }

  padding(num, length) {
    return (Array(length).join("0") + num).slice(-length);
  }

  changeVolume(action) {
    const currentVolume = parseFloat(this.refs.player.volume).toFixed(1);
    const adder = action === "-" ? "-0.1" : "0.1";

    let newVolume = parseFloat(currentVolume) + parseFloat(adder);

    if (newVolume >= 1) {
      newVolume = 1.0;
    } else if (newVolume <= 0) {
      newVolume = 0;
    }

    if (!isFinite(newVolume)) {
      return;
    }

    this.refs.player.volume = newVolume;

    if (this.refs.player.muted && newVolume > 0) {
      this.refs.player.muted = false;
      emitter.emit("loud");
    } else if (!this.refs.player.muted && newVolume <= 0) {
      this.refs.player.muted = true;
      emitter.emit("muted");
    }
  }

  startToPlay() {
    this.refs.player.play();
  }

  pauseToPlay() {
    this.refs.player.pause();
  }

  stopToPlay() {
    this.refs.player.currentTime = 0;
    this.refs.player.play();
  }

  changeVideoSpeaker(muted) {
    this.refs.player.muted = muted;
  }

  videoStatusTogger() {
    if (this.refs.player.paused) {
      this.props.changeVideoPlayingStatus("playing");
      emitter.emit("start");
    } else {
      this.props.changeVideoPlayingStatus("pause");
      emitter.emit("pause");
    }
  }

  goToSpecficTime(percent) {
    const time = percent * this.refs.player.duration;
    if (isFinite(time)) {
      this.refs.player.currentTime = time;
    }
  }

  render() {
    return (
      <Fragment>
        <PlayerHeader title='Lab03 React with Redux Video Player' />
        <main className='player_window'>
          <video
            id='player'
            ref='player'
            onClick={this.videoStatusTogger}
            className='player_window_video'
            src={this.props.selectedItemUrl}
          />
          <div
            id='play-tip'
            onClick={this.videoStatusTogger}
            className={
              this.props.status === "playing" ? "play-tip hidden " : "play-tip"
            }
          >
            <FontAwesomeIcon icon='play' />
          </div>
        </main>
      </Fragment>
    );
  }
}

const mapState = state => ({
  selectedItemUrl:
    state.get("playList").get("selectedItem") &&
    state
      .get("playList")
      .get("selectedItem")
      .get("url")
      ? state
          .get("playList")
          .get("selectedItem")
          .get("url")
      : "",
  status: state.get("playList").get("status")
});

export default connect(
  mapState,
  { changeVideoPlayingStatus, durationUpdated, timeUpdated }
)(PlayerView);
