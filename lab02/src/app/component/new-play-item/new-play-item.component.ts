import { Component, OnInit } from '@angular/core';
import { PlayItem } from 'src/app/service/playItem.model';
import { RestDataSource } from 'src/app/service/rest.datasource';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, AbstractControl, Validators } from '@angular/forms';

@Component({
  selector: 'new-play-item',
  templateUrl: 'new-play-item.component.html',
  styleUrls: ['new-play-item.component.css']
})
export class NewPlayItemComponent implements OnInit {
  newPlayItemInput: FormGroup;

  editPlayItemInput: FormGroup;

  playList: PlayItem[] = [];
  selectedItem = '';

  constructor(private dataSource: RestDataSource,
    private fb: FormBuilder,
    private modalService: NgbModal) {
  }

  get newTitle(): AbstractControl {
    return this.newPlayItemInput.get('title');
  }

  get newUrl(): AbstractControl {
    return this.newPlayItemInput.get('url');
  }

  get eTitle(): AbstractControl {
    return this.editPlayItemInput.get('title');
  }

  get eUrl(): AbstractControl {
    return this.editPlayItemInput.get('url');
  }

  ngOnInit(): void {
    this.newPlayItemInput = this.fb.group(
      {
        title: ['', Validators.required],
        url: ['', [Validators.required, Validators.pattern(/https?:\/\/\w+/)]],
      });
    this.editPlayItemInput = this.fb.group(
      {
        title: [{ value: '', disabled: true }, Validators.required],
        url: [''],
      });
    this.dataSource.getPlayList().subscribe(data => this.playList = data);
  }

  save() {
    if (!this.validate(this.newTitle.value, this.newUrl.value)) {
      return;
    }

    const item = new PlayItem(
      btoa(this.newTitle.value),
      this.newTitle.value,
      this.newUrl.value
    );

    this.dataSource.savePlayItem(item).subscribe(i => {
      this.playList.push(item);
      this.newTitle.setValue('');
      this.newUrl.setValue('');
      this.newPlayItemInput.reset();
    });
  }

  edit(content: string, id: string) {
    const item: PlayItem = this.playList.filter(i => i.id === id)[0];

    this.eTitle.setValue(item.title);
    this.eUrl.setValue(item.url);

    this.modalService.open(content).result.then(action => {
      if (!this.validate(this.eTitle.value, this.eUrl.value)) {
        return;
      }

      this.dataSource.updatePlayItem(id,
        {
          title: this.eTitle.value,
          url: this.eUrl.value,
          approved: 0
        }).subscribe(i => {
          item.title = this.eTitle.value;
          item.url = this.eUrl.value;
          item.approved = 0;
          this.eTitle.setValue('');
          this.eUrl.setValue('');
          this.editPlayItemInput.reset();
        });
    }).catch(action => { });
  }

  delete(content: string, id: string) {
    this.selectedItem = id;
    this.modalService.open(content).result
      .then(action => {
        this.dataSource.deletePlayItemById(id).subscribe(i => {
          this.playList = this.playList.filter(item => item.id !== id);
        });
      })
      .catch(action => { });
  }

  approve(id: string) {
    const item: PlayItem = this.playList.filter(i => i.id === id)[0];
    const updatedItem = JSON.parse(JSON.stringify(item));
    updatedItem.approved = 1;

    if (item) {
      this.dataSource.updatePlayItem(id, updatedItem).subscribe(i => {
        item.approved = 1;
      });
    }
  }

  trackByItems(index: number, item: PlayItem): string {
    return btoa(item.id);
  }

  private validate(title: string, url: string): boolean {
    return Boolean(title) && Boolean(url) && url.search(/https?:\/\/\w+/) === 0;
  }
}
