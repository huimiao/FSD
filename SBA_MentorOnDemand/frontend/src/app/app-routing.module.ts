import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';

import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {SignupComponent} from './signup/signup.component';
import {UserCurrentTrainingsComponent} from './user/user-current-trainings/user-current-trainings.component';
import {UserCompletedTrainingsComponent} from './user/user-completed-trainings/user-completed-trainings.component';
import {MentorCurrentTrainingsComponent} from './mentor/mentor-current-trainings/mentor-current-trainings.component';
import {MentorCompletedTrainingsComponent} from './mentor/mentor-completed-trainings/mentor-completed-trainings.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'usercurrent', component: UserCurrentTrainingsComponent},
  {path: 'usercompleted', component: UserCompletedTrainingsComponent},
  {path: 'mentorcurrent', component: MentorCurrentTrainingsComponent},
  {path: 'mentorcompleted', component: MentorCompletedTrainingsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
