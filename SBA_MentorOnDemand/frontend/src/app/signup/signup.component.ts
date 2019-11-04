import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NewUser} from '../model/new-user';
import {NGXLogger} from 'ngx-logger';
import {AuthService} from '../service/auth-service.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupForm: FormGroup;

  constructor(private fb: FormBuilder,
              private logger: NGXLogger,
              private router: Router,
              private authService: AuthService
  ) {
  }

  ngOnInit() {
    this.signupForm = this.fb.group(
      {
        username: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]],
        first_name: ['', [Validators.required]],
        last_name: ['', [Validators.required]],
        contact_number: ['', [Validators.required]],
        mentor: ['']
      });
  }

  get username(): AbstractControl {
    return this.signupForm.get('username');
  }

  get password(): AbstractControl {
    return this.signupForm.get('password');
  }

  get first_name(): AbstractControl {
    return this.signupForm.get('first_name');
  }

  get last_name(): AbstractControl {
    return this.signupForm.get('last_name');
  }

  get contact_number(): AbstractControl {
    return this.signupForm.get('contact_number');
  }

  get mentor(): AbstractControl {
    return this.signupForm.get('mentor');
  }

  hasError = (controlName: string, errorName: string) => {
    return this.signupForm.controls[controlName].hasError(errorName);
  };

  signup() {
    this.checkValid(this.username, this.password, this.first_name, this.last_name, this.contact_number);
    console.log(9999);
    const user: NewUser = {
      username: this.username.value,
      password: this.password.value,
      firstName: this.first_name.value,
      lastName: this.last_name.value,
      contactNumber: this.contact_number.value,
      isMentor: this.mentor.value ? 'Y' : 'N'
    };

    this.authService.signup(user).subscribe(data => {
        this.router.navigate(['login']);
      },
      error => {
        this.logger.error(error);
      });

  }

  checkValid(...field: AbstractControl[]) {
    field.forEach(f => {
      if (f.invalid) {
        this.logger.error(f + 'is invalid');
        f.markAsTouched();
        f.markAsDirty();
        f.updateValueAndValidity();
      }
    });
  }

}
