import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  searchCondition: string;

  constructor() {
  }

  ngOnInit() {
  }

  setSearchCondition(condition: string) {
    this.searchCondition = condition;
  }

}
