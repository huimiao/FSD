import { Component, Output, EventEmitter, ViewChild, ElementRef, AfterViewInit } from '@angular/core';

@Component({
  selector: 'player',
  templateUrl: 'player.component.html',
  styleUrls: ['player.component.css']
})
export class PlayerComponent implements AfterViewInit  {
  playingVideo = '/';

  @Output()
  playCompleted: EventEmitter<boolean> = new EventEmitter();

  @Output()
  clickOnVideoWindow: EventEmitter<string> = new EventEmitter();

  @Output()
  timeUpdated: EventEmitter<boolean> = new EventEmitter();

  @Output()
  loadedmetadataLoaded: EventEmitter<boolean> = new EventEmitter();

  @ViewChild('playerWindow', { static: false })
  playerWindowRef: ElementRef;

  @ViewChild('playTip', { static: false })
  playTipRef: ElementRef;

  get playTip() {
    return this.playTipRef.nativeElement;
  }

  get playerWindow() {
    return this.playerWindowRef.nativeElement;
  }

  ngAfterViewInit(): void {
    this.playTip.addEventListener('click', () => this.clickOnVideoWindow.emit('start'), false);
    this.playerWindow.addEventListener('click', () => {
      this.playerWindow.paused ? this.clickOnVideoWindow.emit('start') : this.clickOnVideoWindow.emit('pause');
    }, false);
    this.playerWindow.addEventListener('ended', () => this.playCompleted.emit(true), false);
    this.playerWindow.addEventListener('timeupdate', () => this.timeUpdated.emit(true), false);
    this.playerWindow.addEventListener('loadedmetadata', () => this.loadedmetadataLoaded.emit(true), false);
  }
}
