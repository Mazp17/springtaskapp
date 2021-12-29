import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
//TODO: Implementar metodos de inicio de sesión
export class LoginComponent implements OnInit {

  public loginWrong: boolean = false;
  public loginUser: FormGroup;
  constructor(public fb: FormBuilder, public apiService: ApiService, public authService: AuthService, private router: Router) {
    this.loginUser = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', Validators.required]
  
    })
    if(this.authService.isAuthenticated()) {
      this.router.navigate(['/home']);
      swal.fire('Login', 'Hola ' + this.authService.usuario.username + ' Ya iniciaste sesión!', 'info');
    }
  }

  ngOnInit(): void {
    if(this.authService.isAuthenticated()) {
      this.router.navigate(['/home']);
      swal.fire('Login', 'Hola ' + this.authService.usuario.username + ' Ya iniciaste sesión!', 'info');
    }
  }


  onSubmit() { 
    console.log(this.loginUser.value);

    this.authService.login(this.loginUser.value).subscribe(response => {
      console.log(response);
      
      this.authService.guardarUsuario(response.access_token);
      this.authService.guardarToken(response.access_token);
      let user = this.authService.usuario;
      this.loginWrong = false;
      this.router.navigate(["/home"]);

      swal.fire('Inicio de sesión', `Hola, ${user.username}.
      Has iniciado sesión satisfactoriamente.`, 'success');
      
      return;
    }, err => {
      if(err.status == 400) {
        this.loginWrong = true;
      }
    }
    );
  }

  get f() {
    return this.loginUser.controls;
  }
}
