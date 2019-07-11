import { Component, Output, OnInit, EventEmitter } from '@angular/core';
import { PlayItem } from '../../../service/playItem.model';
import { RestDataSource } from 'src/app/service/rest.datasource';

@Component({
  selector: 'play-list',
  templateUrl: 'play-list.component.html',
  styleUrls: ['play-list.component.css']
})
export class PlayListComponent implements OnInit {
  selectedItem = '/';
  playList: PlayItem[] = [];

  @Output()
  playItemSelected: EventEmitter<PlayItem> = new EventEmitter();
  @Output()
  playListReady: EventEmitter<PlayItem[]> = new EventEmitter();

  constructor(private dataSource: RestDataSource) { }

  ngOnInit(): void {
    this.dataSource.getPlayList().subscribe(data => {
      this.playList = data;
      this.playListReady.emit(data);
    });
  }

  loadVideo(title: string) {
    this.selectedItem = title;
    this.playItemSelected.emit(this.getVideoUrl(title));
  }

  private getVideoUrl(title: string): PlayItem {
    return this.playList.filter(i => i.title === title)[0];
  }
}
