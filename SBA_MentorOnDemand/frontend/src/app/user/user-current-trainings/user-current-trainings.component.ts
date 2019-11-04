import {Component, OnInit} from '@angular/core';
import {TrainingServiceService} from '../../service/training-service.service';
import {Router} from '@angular/router';
import {NGXLogger} from 'ngx-logger';
import {FormBuilder} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {TrainingListItem} from '../../model/training-list-item';
import {ErrorComponent} from '../../component/error/error.component';
import {NotificationComponent} from '../../component/notification/notification.component';
import {UserTrainingListItem} from '../../model/user-training-list-item';

@Component({
  selector: 'app-user-current-trainings',
  templateUrl: './user-current-trainings.component.html',
  styleUrls: ['./user-current-trainings.component.css']
})
export class UserCurrentTrainingsComponent implements OnInit {
  private trainings: UserTrainingListItem[];
  private dataReady: boolean;

  searchCondition: string;

  constructor(private trainingService: TrainingServiceService,
              private router: Router,
              private logger: NGXLogger,
              private fb: FormBuilder,
              private modal: NgbModal) {
  }

  ngOnInit() {
    if (localStorage.getItem('current_user')) {
      this.trainingService.getAllTrainings(localStorage.getItem('current_user'), 'ONGOING').subscribe(
        data => {
          if (data.code === 'SUCCESS') {
            this.trainings = data.trainings;
          }
          this.dataReady = true;
        },
        error => {
          this.logger.error(error);
          this.modal.open(ErrorComponent);
        }
      );
    } else {
      this.router.navigate(['login']);
    }
  }

  changeRating(id: number, event) {

    let same = false;
    this.trainings.forEach(t => {
      if (t.id === id && t.rating === event) {
        same = true;
        return;
      }
    });

    if (same) {
      return;
    }

    this.trainingService.rating(id, event, localStorage.getItem('current_user')).subscribe(
      data => {
        if (data.code === 'SUCCESS') {
          this.modal.open(NotificationComponent);
        }
      },
      error => {
        this.logger.error(error);
        this.modal.open(ErrorComponent);
      }
    );
  }

  setSearchCondition(condition: string) {
    this.searchCondition = condition;
  }

}
