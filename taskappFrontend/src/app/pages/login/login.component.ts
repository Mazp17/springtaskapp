import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from 'src/app/services/api.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
//TODO: Implementar metodos de inicio de sesión
export class LoginComponent implements OnInit {

  public loginUser: FormGroup;
  constructor(public fb: FormBuilder, public apiService: ApiService) {
    this.loginUser = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', Validators.required]
    })
  }

  ngOnInit(): void {
  }


  onSubmit() { 

  }
}
