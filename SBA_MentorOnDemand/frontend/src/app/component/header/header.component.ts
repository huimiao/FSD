import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {NGXLogger} from 'ngx-logger';
import {AuthService} from '../../service/auth-service.service';
import {Technology} from '../../model/technology';
import {TechnologyService} from '../../service/technology-service.service';
import {NgbModal, NgbModalOptions} from '@ng-bootstrap/ng-bootstrap';
import {NewTrainingComponent} from '../new-training/new-training.component';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private isAuthenticated: boolean;
  private username: string;
  private role: string;
  private technologies: Technology[] = [];
  @ViewChild('searchCondition', {static: false})
  searchCondition: ElementRef;
  @Output()
  searchConditionChanged: EventEmitter<string> = new EventEmitter();

  constructor(private authService: AuthService,
              private technologyService: TechnologyService,
              private logger: NGXLogger,
              private modal: NgbModal) {
    if (localStorage.getItem('current_user')) {
      this.isAuthenticated = true;
      this.username = localStorage.getItem('current_user');
    }

    if (localStorage.getItem('current_user_role')) {
      this.role = localStorage.getItem('current_user_role');
    }
  }

  ngOnInit() {
    this.technologyService.getAllTechnologies().subscribe(resp => {
        if (resp.success) {
          this.technologies = resp.technologies;
        }
      },
      error => {
        this.logger.log(error);
        this.logger.error('Cannot get the technology list from the server');
      },
      () => console.log('c'));
  }

  logout() {
    this.logger.log(localStorage.getItem('current_user') + ' logout');
    this.authService.logout();
    location.replace('/');
  }

  newTraining() {
    const options: NgbModalOptions = {
      backdrop: 'static'
    };

    this.modal.open(NewTrainingComponent, options);
  }

  search() {
    if (this.searchCondition.nativeElement.value === 'Choose') {
      return;
    } else {
      this.searchConditionChanged.emit(this.searchCondition.nativeElement.value);
    }
  }
}
