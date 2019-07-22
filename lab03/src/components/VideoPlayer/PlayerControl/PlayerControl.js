import React, { Component, Fragment } from "react";
import { connect } from "react-redux";
import { emitter } from "../common";
import { changeVideoPlayingStatus } from "../PlayerList/store/actionCreators";
import {
  toggleSpeaker,
  updateScore,
  updatePlayItem
} from "./store/actionCreators";
import { Repository } from "../../../services";
import { Score } from "../../../models";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

import "./PlayerControl.css";

class PlayerControl extends Component {
  constructor(props) {
    super(props);
    this.toggleSpeaker = this.toggleSpeaker.bind(this);
    this.repository = new Repository();
    this.props.onRef(this);
  }

  componentDidMount() {
    emitter.addListener("itemSelected", item => {
      let sc = this.repository.loadScoreFromDB(item.get("id"));
      if (!(sc.disLikeScore || sc.likeScore)) {
        sc = new Score(item.get("likes"), item.get("unlikes"));
      }

      this.props.updateScore(sc);

      let lastPlayed = this.repository.loadFromDB(item.get("url"));
      
      if(lastPlayed ){
        emitter.emit("goToLastPlayed", lastPlayed);
      }        
    });
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

  getProgress() {
    return this.props.duration === 0
      ? 0
      : Math.ceil((100 / this.props.duration) * this.props.currentTime);
  }

  toggleSpeaker() {
    if (this.props.speakerStatus === "on") {
      this.props.toggleSpeaker("mute");
      emitter.emit("speakerMute", true);
    } else {
      this.props.toggleSpeaker("on");
      emitter.emit("speakerMute", false);
    }
  }

  goToSpecifiedTime(e) {
    const to = e.nativeEvent.offsetX;
    const width = e.target.offsetWidth;
    emitter.emit("progressBarClicked", to / width);
  }

  getSpeckerIcoStyle() {
    if (this.props.speakerStatus === "on") {
      return "volume-up";
    } else {
      return "volume-mute";
    }
  }

  updateScore(type) {
    let sc = {
      likeScore: this.props.likeNum,
      disLikeScore: this.props.disLikeNum
    };
    if (type === "+") {
      sc.likeScore += 1;
    } else {
      sc.disLikeScore += 1;
    }
    this.props.updatePlayItem(this.props.selectedItem.get("id"), {
      likes: sc.likeScore,
      unlikes: sc.disLikeScore
    });
  }

  render() {
    return (
      <Fragment>
        <footer className='playerControls'>
          <div className='progress2'>
            <progress
              id='progressBar'
              className='progress'
              value={this.getProgress()}
              onClick={this.goToSpecifiedTime}
              max='100'
            />
          </div>
          <div id='controll' className='controll'>
            <ul>
              <li
                id='startBtn'
                className={this.props.status === "playing" ? "disabled" : ""}
                onClick={() => {
                  this.props.changeVideoPlayingStatus("playing");
                  emitter.emit("start");
                }}
              >
                <FontAwesomeIcon icon='play' />
              </li>
              <li
                id='pauseBtn'
                className={this.props.status === "playing" ? "" : "disabled"}
                onClick={() => {
                  this.props.changeVideoPlayingStatus("pause");
                  emitter.emit("pause");
                }}
              >
                <FontAwesomeIcon icon='pause' />
              </li>
              <li
                id='reloadBtn'
                className={this.props.status === "playing" ? "" : "disabled"}
                onClick={() => {
                  this.props.changeVideoPlayingStatus("playing");
                  emitter.emit("stop");
                }}
              >
                <FontAwesomeIcon icon='stop' />
              </li>
              <li
                id='volumeUpBtn'
                onClick={() => {
                  emitter.emit("volumeUp");
                }}
              >
                <FontAwesomeIcon icon='plus' />
              </li>
              <li
                id='volumeDownBtn'
                onClick={() => {
                  emitter.emit("volumeDown");
                }}
              >
                <FontAwesomeIcon icon='minus' />
              </li>
              <li id='speakerBtn' ref='speaker' onClick={this.toggleSpeaker}>
                <FontAwesomeIcon icon={this.getSpeckerIcoStyle()} />
              </li>
              <li
                id='likeBtn'
                className='addNumber'
                style={{ marginRight: "10px" }}
                onClick={() => this.updateScore("+")}
              >
                <FontAwesomeIcon icon='thumbs-up' style={{ color: "green" }} />
                <span id='likeNum'>{this.props.likeNum}</span>
              </li>
              <li
                id='disLikeBtn'
                className='addNumber'
                style={{ marginRight: "10px" }}
                onClick={() => this.updateScore("-")}
              >
                <FontAwesomeIcon icon='thumbs-down' style={{ color: "red" }} />
                <span id='disLikeNum'>{this.props.disLikeNum}</span>
              </li>
              <li id='time_progress' className='time_progress'>
                {" "}
                <span id='current_time'>
                  {this.formatTime(this.props.currentTime)}
                </span>
                /
                <span id='total_time'>
                  {this.formatTime(this.props.duration)}
                </span>
              </li>
            </ul>
          </div>
        </footer>
      </Fragment>
    );
  }
}

const mapState = state => ({
  status: state.get("playList").get("status"),
  selectedItem: state.get("playList").get("selectedItem"),
  currentTime: state.get("playView").get("currentTime"),
  duration: state.get("playView").get("duration"),
  speakerStatus: state.get("playControl").get("speakerStatus"),
  likeNum: state.get("playControl").get("likeNum"),
  disLikeNum: state.get("playControl").get("disLikeNum")
});

export default connect(
  mapState,
  { updatePlayItem, updateScore, toggleSpeaker, changeVideoPlayingStatus }
)(PlayerControl);
