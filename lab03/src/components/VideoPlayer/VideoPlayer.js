import React, { Component } from "react";
import PlayerView from "./PlayerView";
import PlayerControl from "./PlayerControl";
import PlayerList, { NewVideo } from "./PlayerList";
import { emitter } from "./common";
import "./VideoPlayer.css";

class VideoPlayer extends Component {
  onPlayerViewRef = ref => {
    this.playerView = ref;
  };

  onPlayerControlRef = ref => {
    this.playerControl = ref;
  };

  componentDidMount() {
    emitter.addListener("start", () => {
      this.playerView.startToPlay();
    });

    emitter.addListener("pause", () => {
      this.playerView.pauseToPlay();
    });

    emitter.addListener("stop", () => {
      this.playerView.stopToPlay();
    });

    emitter.addListener("timeupdate", () => {
      this.playerControl.updateProgress();
    });

    emitter.addListener("volumeUp", () => {
      this.playerView.changeVolume("+");
    });

    emitter.addListener("volumeDown", () => {
      this.playerView.changeVolume("-");
    });

    emitter.addListener("muted", () => {
      this.playerControl.toggleSpeaker();
    });

    emitter.addListener("loud", () => {
      this.playerControl.toggleSpeaker();
    });

    emitter.addListener("speakerMute", muted => {
      this.playerView.changeVideoSpeaker(muted);
    });

    emitter.addListener("progressBarClicked", percent => {
      this.playerView.goToSpecficTime(percent);
    });
  }

  render() {
    return (
      <div className='container-fluid'>
        <div className='row'>
          <div className='col-lg-1 col-md-12' />
          <div className='col-lg-8 col-md-12'>
            <div className='player-content player-content-window'>
              <PlayerView onRef={this.onPlayerViewRef} />
              <PlayerControl onRef={this.onPlayerControlRef} />
            </div>
          </div>
          <div className='col-lg-2 col-md-12'>
            <PlayerList title='Play List' />
          </div>
          <div>
            <NewVideo />>
          </div>
        </div>
        <div className='col-lg-1 col-md-12' />
      </div>
    );
  }
}

export default VideoPlayer;
