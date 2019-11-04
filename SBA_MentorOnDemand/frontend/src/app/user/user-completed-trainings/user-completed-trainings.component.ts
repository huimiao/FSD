import {Component, OnInit} from '@angular/core';
import {TrainingServiceService} from '../../service/training-service.service';
import {Router} from '@angular/router';
import {NGXLogger} from 'ngx-logger';
import {FormBuilder} from '@angular/forms';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {ErrorComponent} from '../../component/error/error.component';
import {UserTrainingListItem} from '../../model/user-training-list-item';

@Component({
  selector: 'app-user-completed-trainings',
  templateUrl: './user-completed-trainings.component.html',
  styleUrls: ['./user-completed-trainings.component.css']
})
export class UserCompletedTrainingsComponent implements OnInit {
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
      this.trainingService.getAllTrainings(localStorage.getItem('current_user'), 'COMPLETED').subscribe(
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

  setSearchCondition(condition: string) {
    this.searchCondition = condition;
  }
}
