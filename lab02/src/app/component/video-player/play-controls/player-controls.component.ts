import { Component, OnInit, ViewChild, ElementRef, Injectable, Output, EventEmitter } from '@angular/core';
import { PlayItem, Score } from 'src/app/service';

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
  @Output()
  thumbsClicked: EventEmitter<boolean> = new EventEmitter();

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
  selctedPlayItem: PlayItem;
  currentScore: Score;

  get socreKey(): string {
    return `${this.selctedPlayItem.url}/score`;
  }

  get timeKey(): string {
    return `${this.selctedPlayItem.url}/time`;
  }

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
    this.currentScore = new Score();
  }

  loadPlayItem(playItem: PlayItem) {
    this.resetControls();
    this.selctedPlayItem = playItem;

    const sc: Score = this.loadScoreFromDB(this.socreKey);

    if (!(sc.disLikeScore || sc.likeScore)) {
      this.currentScore = new Score(playItem.likes, playItem.unlikes);
    } else {
      this.currentScore = sc;
    }

    this.saveToDB('playing', playItem.url);
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
    this.stopped = false;
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
    this.saveCurrentTimeToDB(currentTime);
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

  thumbsClickedHandler(type: string) {
    if (type === 'like' || type === 'disLike') {
      this.currentScore = this.saveScoreToDB(this.socreKey, type, new Score(this.selctedPlayItem.likes, this.selctedPlayItem.unlikes));
    }
  }

  saveCurrentTimeToDB(time: number) {
    this.saveToDB(this.timeKey, time.toString());
  }

  saveToDB(key: string, value: string) {
    localStorage.setItem(key, value);
  }

  loadFromDB(key: string) {
    const value = localStorage.getItem(key);
    return value;
  }

  loadScoreFromDB(src: string) {
    if (!src || src === '') {
      return;
    }

    const score = this.loadFromDB(src);

    return (score && JSON.parse(score)) || new Score();
  }

  saveScoreToDB(src: string, type: string, score: Score): Score {
    if (!src || src === '' || !type) {
      return;
    }

    let sc: Score = this.loadScoreFromDB(src);

    if (!(sc.likeScore || sc.disLikeScore)) {
      sc = score;
    }
    if (type === 'like') {
      sc.likeScore += 1;
    } else if (type === 'disLike') {
      sc.disLikeScore += 1;
    }

    this.saveToDB(src, JSON.stringify(sc));
    return sc;
  }

  lastPlayedVideo(): string {
    return this.loadFromDB('playing') || '';
  }
}
