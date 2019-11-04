import {Component, OnInit} from '@angular/core';
import {UserTrainingListItem} from '../../model/user-training-list-item';
import {TrainingServiceService} from '../../service/training-service.service';
import {Router} from '@angular/router';
import {NGXLogger} from 'ngx-logger';
import {FormBuilder} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ErrorComponent} from '../../component/error/error.component';
import {NotificationComponent} from '../../component/notification/notification.component';
import {TrainingListItem} from '../../model/training-list-item';

@Component({
  selector: 'app-mentor-current-trainings',
  templateUrl: './mentor-current-trainings.component.html',
  styleUrls: ['./mentor-current-trainings.component.css']
})
export class MentorCurrentTrainingsComponent implements OnInit {

  private trainings: TrainingListItem[];
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
      this.trainingService.getAllMentorTrainings(localStorage.getItem('current_user'), 'ONGOING').subscribe(
        data => {
          if (data.code === 'SUCCESS') {
            this.trainings = data.mentors;
            console.log(this.trainings);
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

  setSearchCondition(condition: string) {
    this.searchCondition = condition;
  }
}
