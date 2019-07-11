import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { PlayerComponent, PlayerControllersComponent, PlayListComponent } from './video-player';
import { PlayItem } from '../service';

@Component({
  selector: 'app-video-player',
  templateUrl: 'video-player.component.html',
  styleUrls: ['video-player.component.css']
})
export class VideoPlayerComponent implements AfterViewInit {
  @ViewChild('player', { static: false })
  playerView: PlayerComponent;

  @ViewChild('playerControl', { static: false })
  playerControllersComponent: PlayerControllersComponent;

  @ViewChild('playList', { static: false })
  playListComponent: PlayListComponent;

  private player;
  private control: PlayerControllersComponent;

  ngAfterViewInit(): void {
    this.player = this.playerView.playerWindow;
    this.control = this.playerControllersComponent;
  }

  playItemSelectedHandler(playItem: PlayItem) {
    this.control.loadPlayItem(playItem);
    this.playerView.playingVideo = playItem.url;
  }

  startToPlayClickedHandler() {
    this.player.play();
  }

  pauseToPlayClickedHandler() {
    this.player.pause();
  }

  stopToPlayClickedHandler() {
    this.player.pause();
    this.player.currentTime = 0;
  }

  playCompletedHandler() {
    this.control.resetControls();
  }

  timeUpdatedHandler() {
    this.control.updateProgress(this.player.duration, this.player.currentTime);
  }

  loadedmetadataLoadedHandler() {
    this.control.totalTime.innerHTML
      = this.playerControllersComponent.formatTime(this.player.duration);
  }

  clickOnVideoWindowHandler(action: string) {
    if (action === 'start') {
      this.control.startToPlay();
    } else if (action === 'pause') {
      this.control.pausePlay();
    }
  }

  volumeChangeClickedHandler(action: string) {
    console.log(123);
    const currentVolume = parseFloat(this.player.volume).toFixed(1);
    const adder = (action === '-') ? '-0.1' : '0.1';

    let newVolume = parseFloat(currentVolume) + parseFloat(adder);

    if (newVolume >= 1) {
      newVolume = 1.0;
    } else if (newVolume <= 0) {
      newVolume = 0;
    }

    if (!isFinite(newVolume)) {
      return;
    }

    this.player.volume = newVolume;

    console.log(newVolume);
    if (this.player.muted && newVolume > 0) {
      this.player.muted = false;
      this.control.muted = false;
    } else if (!this.player.muted && newVolume <= 0) {
      this.player.muted = true;
      this.control.muted = true;
    }
  }

  toggleSpeakerClickedHandler(muted: boolean) {
    this.player.muted = muted;
  }

  progressBarClickedHandler(percent: number) {
    const time = percent * this.player.duration;
    if (isFinite(time)) {
      this.player.currentTime = time;
    }
  }
}
