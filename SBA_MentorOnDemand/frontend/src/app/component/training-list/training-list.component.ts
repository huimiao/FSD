import {Component, Input, OnInit} from '@angular/core';
import {TrainingServiceService} from '../../service/training-service.service';
import {NGXLogger} from 'ngx-logger';
import {FormBuilder} from '@angular/forms';
import {TrainingListItem} from '../../model/training-list-item';
import {Router} from '@angular/router';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {NotificationComponent} from '../notification/notification.component';
import {ErrorComponent} from '../error/error.component';

@Component({
  selector: 'app-training-list',
  templateUrl: './training-list.component.html',
  styleUrls: ['./training-list.component.css']
})
export class TrainingListComponent implements OnInit {

  private trainings: TrainingListItem[];
  private dataReady: boolean;
  @Input()
  private searchCondition: string;

  constructor(private trainingService: TrainingServiceService,
              private router: Router,
              private logger: NGXLogger,
              private fb: FormBuilder,
              private modal: NgbModal) {
  }

  ngOnInit() {
    this.trainingService.getALlMentorTrainings().subscribe(
      data => {
        if (data.code === 'SUCCESS') {
          this.trainings = data.mentors;
        }
        this.dataReady = true;
      },

      error => {
        this.logger.error(error);
        this.modal.open(ErrorComponent);
      }
    );
  }


  propose(id: number) {
    if (!localStorage.getItem('current_user')) {
      this.router.navigate(['login']);
    } else {
      this.trainingService.proposeTraining(id, localStorage.getItem('current_user')).subscribe(data => {
          this.modal.open(NotificationComponent);
        },
        error => {
          console.log(error);
          this.modal.open(ErrorComponent);
        });
    }
  }
}
