import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent{
  constructor(private router: Router,
    public authService: AuthService) {
  }

  logout(): void {

    //let token = this.authService.token;
    //console.log(token.split(".")[1])
    //console.log(this.authService.token);

    
    let username = this.authService.usuario.username;
    this.authService.logout();
    Swal.fire('Nos vemos pronto!',
     'Tu sesión se ha cerrado satisfactoriamente, hasta luego ' + username,
     'info');
    this.router.navigate(['/login']);

    
  }
  


}
