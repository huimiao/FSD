import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../service/auth-service.service';
import {NGXLogger} from 'ngx-logger';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;
  returnUrl: string;

  constructor(private fb: FormBuilder,
              private authService: AuthService,
              private router: Router,
              private route: ActivatedRoute,
              private logger: NGXLogger) {
  }

  ngOnInit() {
    this.loginForm = this.fb.group(
      {
        username: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]]
      });
    this.returnUrl = this.route.snapshot.queryParams.returnUrl || '/';

    if (localStorage.getItem('current_user')) {
      this.router.navigate([this.returnUrl]);
    }
  }

  get username(): AbstractControl {
    return this.loginForm.get('username');
  }

  get password(): AbstractControl {
    return this.loginForm.get('password');
  }

  hasError = (controlName: string, errorName: string) => {
    return this.loginForm.controls[controlName].hasError(errorName);
  };

  login() {
    let validUsername: boolean;
    let validPassword: boolean;
    validUsername = this.checkIfValid(this.username);
    validPassword = this.checkIfValid(this.password);

    if (!validUsername || !validPassword) {
      return;
    }

    this.authService.login(this.username.value, this.password.value)
      .subscribe(username => {
          this.logger.log(username);
          this.logger.log(this.returnUrl);
          this.router.navigate([this.returnUrl]);
        },
        error => {
          this.logger.error('Username or Password invalid');
        });
  }

  checkIfValid(field): boolean {
    if (field.invalid) {
      field.markAsTouched();
      field.markAsDirty();
      field.updateValueAndValidity();
      return false;
    }
    return true;
  }
}
