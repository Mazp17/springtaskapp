import { Component, OnInit } from '@angular/core';
import { AbstractControl, Form, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { User } from 'src/app/interfaces/user.interface';
import { ApiService } from 'src/app/services/api.service';
import { UserComponent } from '../user/user.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
//TODO: Implementar metodos de registro
export class RegisterComponent implements OnInit {
  errorUser: boolean = false;
  error: boolean = false;
  submit: boolean; 
  passNotSame: any = false;
  passNull: any = false;
  messageErrorBack: String = ""; 

  passwordErrorValidator(control: any = AbstractControl) {
    const password: any = control.get('password').value; // get password from our password form control
    const confirmPassword: any = control.get('confirmPassword').value; // get password from our confirmPassword form control
    if (password === "" || confirmPassword === "") {
      return control.get('password').setErrors({passNull: true})
    } else {
      control.get('password').setErrors({passNull: false});
      if(password === confirmPassword) {
        control.get('password').setErrors(null)
        return null;
        
      } else {
        return control.get('confirmPassword').setErrors({passNotSame: true});
      }
    }
  }
  public registerUser: FormGroup;
   
  constructor(public apiService: ApiService, public fb: FormBuilder) {
    this.registerUser = this.fb.group({
      username : ['', [Validators.required]],
      email: ['', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]],
      password: ['', [Validators.required], ],
      confirmPassword: ['', [Validators.required]]
    }, 
    { validator: this.passwordErrorValidator}
    );
    this.submit = false;

   }

  ngOnInit(): void {  
  }

  onSubmit(data: User) {
    this.apiService.registerUser({
      name: data.username,
      email: data.email,
      password: data.password      
    }).subscribe(
      () => {
        this.error = false;
        this.errorUser = false;
        this.submit = true; 
      },
      (error) => {
        console.error(error);
        this.error = true;
        this.messageErrorBack = error.error.message;
        switch (error.error.code) {
          case "userExists": {
            this.errorUser = true;
            break;
          }
          default: {
          }
        }

      }
    );
  } 

  get f() {
    return this.registerUser.controls;
  }
}
