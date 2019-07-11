import { Component, Input } from '@angular/core';

@Component({
  selector: 'common-header',
  templateUrl: 'common-header.component.html',
  styleUrls: ['common-header.component.css']
})
export class CommonHeaderComponent {
  @Input()
  title: string;

  getTitle(): string {
    return this.title;
  }
}
