import { Component, OnInit, ViewChild, ElementRef, Injectable, Output, EventEmitter } from '@angular/core';
import { PlayItem } from 'src/app/service';

@Component({
  selector: 'player-controls',
  templateUrl: 'player-controls.component.html',
  styleUrls: ['player-controls.component.css']
})
@Injectable()
export class PlayerControllersComponent implements OnInit {
  @Output()
  startToPlayClicked: EventEmitter<boolean> = new EventEmitter();
  @Output()
  pauseToPlayClicked: EventEmitter<boolean> = new EventEmitter();
  @Output()
  stopToPlayClicked: EventEmitter<boolean> = new EventEmitter();
  @Output()
  volumeChangeClicked: EventEmitter<string> = new EventEmitter();
  @Output()
  toggleSpeakerClicked: EventEmitter<boolean> = new EventEmitter();
  @Output()
  progressBarClicked: EventEmitter<number> = new EventEmitter();

  @ViewChild('progressBar', { static: false })
  progressBarRef: ElementRef;
  @ViewChild('currentTime', { static: false })
  currentTimeRef: ElementRef;
  @ViewChild('totalTime', { static: false })
  totalTimeRef: ElementRef;
  @ViewChild('likeNum', { static: false })
  likeNumRef: ElementRef;
  @ViewChild('disLikeNum', { static: false })
  disLikeNumRef: ElementRef;

  stopped: boolean;
  muted: boolean;

  get progressBar() {
    return this.progressBarRef.nativeElement;
  }

  get currentTime() {
    return this.currentTimeRef.nativeElement;
  }

  get totalTime() {
    return this.totalTimeRef.nativeElement;
  }

  get likeNumField() {
    return this.likeNumRef.nativeElement;
  }

  get disLikeNumField() {
    return this.disLikeNumRef.nativeElement;
  }

  ngOnInit(): void {
    this.stopped = true;
  }

  loadPlayItem(playItem: PlayItem) {
    this.resetControls();
    this.likeNumField.innerHTML = playItem.likes;
    this.disLikeNumField.innerHTML = playItem.unlikes;
  }

  startToPlay() {
    this.stopped = false;
    this.startToPlayClicked.emit(true);
  }

  pausePlay() {
    this.stopped = true;
    this.pauseToPlayClicked.emit(true);
  }

  stopPlay() {
    this.resetControls();
    this.stopToPlayClicked.emit(true);
  }

  changeVolume(action: string) {
    this.volumeChangeClicked.emit(action);
  }

  toggleSpeaker() {
    this.muted = !this.muted;
    this.toggleSpeakerClicked.emit(this.muted);
  }

  updateProgress(duration: number, currentTime: number) {
    const percentage = Math.ceil((100 / duration) * currentTime);

    if (isNaN(percentage) || !isFinite(percentage)) {
      return;
    }

    this.progressBar.value = percentage;
    this.currentTime.innerHTML = this.formatTime(currentTime);
  }

  resetControls() {
    this.stopped = true;
    this.progressBar.value = 0;
    this.currentTime.innerHTML = '00:00:00';
  }

  formatTime(time: number) {
    const h = Math.floor(time / 3600);
    const m = Math.floor(time % 3600 / 60);
    const s = Math.floor(time % 60);
    return this.padding(h, 2) + ':' + this.padding(m, 2) + ':' + this.padding(s, 2);
  }

  padding(num: number, length: number) {
    return (Array(length).join('0') + num).slice(-length);
  }

  goToSpecifiedTime(e) {
    const to = e.offsetX;
    const width = e.target.offsetWidth;
    this.progressBarClicked.emit(to / width);
  }
}
