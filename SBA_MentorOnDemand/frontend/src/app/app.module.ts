import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {AppRoutingModule} from './app-routing.module';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule, FormBuilder, ReactiveFormsModule} from '@angular/forms';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {SignupComponent} from './signup/signup.component';
import {HomeComponent} from './home/home.component';
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {JwtInterceptor} from './interceptor/jwt.interceptor';
import {LoggerModule, NgxLoggerLevel} from 'ngx-logger';
import {HeaderComponent} from './component/header/header.component';
import {NewTrainingComponent} from './component/new-training/new-training.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {TrainingListComponent} from './component/training-list/training-list.component';
import {NotificationComponent} from './component/notification/notification.component';
import {ErrorComponent} from './component/error/error.component';
import {UserCurrentTrainingsComponent} from './user/user-current-trainings/user-current-trainings.component';
import {UserCompletedTrainingsComponent} from './user/user-completed-trainings/user-completed-trainings.component';
import {Ng2SearchPipeModule} from 'ng2-search-filter';
import { MentorCurrentTrainingsComponent } from './mentor/mentor-current-trainings/mentor-current-trainings.component';
import { MentorCompletedTrainingsComponent } from './mentor/mentor-completed-trainings/mentor-completed-trainings.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    HeaderComponent,
    NewTrainingComponent,
    TrainingListComponent,
    NotificationComponent,
    ErrorComponent,
    UserCurrentTrainingsComponent,
    UserCompletedTrainingsComponent,
    MentorCurrentTrainingsComponent,
    MentorCompletedTrainingsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatInputModule,
    HttpClientModule,
    LoggerModule.forRoot({
      level: NgxLoggerLevel.DEBUG,
      disableConsoleLogging: false
    }),
    NgbModule,
    ReactiveFormsModule.withConfig({warnOnNgModelWithFormControl: 'never'}),
    Ng2SearchPipeModule
  ],
  providers: [FormBuilder,
    {provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}],
  bootstrap: [AppComponent],
  entryComponents: [NewTrainingComponent, NotificationComponent, ErrorComponent]
})
export class AppModule {
}
