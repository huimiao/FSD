import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NgbActiveModal, NgbTimepickerConfig, NgbTimeStruct} from '@ng-bootstrap/ng-bootstrap';
import {Technology} from '../../model/technology';
import {TechnologyService} from '../../service/technology-service.service';
import {NGXLogger} from 'ngx-logger';
import {Training} from '../../model/training';
import {formatDate, Time} from '@angular/common';
import {TrainingServiceService} from '../../service/training-service.service';

@Component({
  selector: 'app-new-training',
  templateUrl: './new-training.component.html',
  styleUrls: ['./new-training.component.css'],
  providers: [NgbTimepickerConfig]
})
export class NewTrainingComponent implements OnInit {
  private technologiesNew: Technology[];
  private yearOfExpNew: number[];
  newTrainingInput: FormGroup;
  startDate;
  endDate;
  endTime: NgbTimeStruct = {hour: 9, minute: 0, second: 0};
  startTime: NgbTimeStruct = {hour: 18, minute: 0, second: 0};

  constructor(
    private trainingService: TrainingServiceService,
    private logger: NGXLogger,
    private fb: FormBuilder,
    public modal: NgbActiveModal,
    private technologyService: TechnologyService,
    private config: NgbTimepickerConfig) {
    this.newTrainingInput = this.fb.group(
      {
        technology_new: ['0'],
        yearOfExp_new: ['10', [Validators.required]],
        startTime: [''],
        endTime: [''],
        startDate: [''],
        endDate: [''],
        profile: ['say something about yourself', [Validators.required]],
        fee: ['500', [Validators.required]]
      });
    config.seconds = false;
    config.spinners = false;
  }

  get technology(): AbstractControl {
    return this.newTrainingInput.get('technology_new');
  }

  get yearOfExp(): AbstractControl {
    return this.newTrainingInput.get('yearOfExp_new');
  }

  get profile(): AbstractControl {
    return this.newTrainingInput.get('profile');
  }

  get fee(): AbstractControl {
    return this.newTrainingInput.get('fee');
  }

  ngOnInit() {
    this.technologyService.getAllTechnologies().subscribe(resp => {
        if (resp.success) {
          this.technologiesNew = resp.technologies;
        }
      },
      error => {
        this.logger.log(error);
        this.logger.error('Cannot get the technology list from the server');
      },
      () => console.log('c'));

    this.yearOfExpNew = Array.from({length: 30}, (item, index) => index + 1);
  }

  save() {
    const newTraining: Training = {
      musername: localStorage.getItem('current_user'),
      sname: this.technology.value,
      yearOfExp: this.yearOfExp.value,
      startTime: this.startTime.hour + ':' + this.startTime.minute + ':' + this.startTime.minute,
      endTime: this.endTime.hour + ':' + this.endTime.minute + ':' + this.endTime.minute,
      startDate: this.startDate.year + '-' + this.startDate.month + '-' + this.startDate.day,
      endDate: this.endDate.year + '-' + this.endDate.month + '-' + this.endDate.day,
      fee: this.fee.value,
      profile: this.profile.value
    };

    this.trainingService.addNewTraining(newTraining).subscribe(
      data => {
        if (data.code === 'SUCCESS') {
          this.modal.close();
        }
      },

      error => {
        this.logger.error(error);
      }
    );

    console.log(newTraining);
  }
}
