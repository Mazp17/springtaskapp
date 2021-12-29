import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})

// esta clase prueba si el usuario tiene token, si no lo redirecciona al login
//si lo tiene, revisa si esta expirado, si esta expirado lo reenvia a login, si no lo deja continuar. 

export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService,
    private router: Router){

  }

  //la funcion canActiva es el metodo que realiza lo definido anteriormente, devuelve un boolean
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    if(this.authService.isAuthenticated()) {
      if(this.isTokenExpired()) {
        this.authService.logout();
        this.router.navigate(['/login']);
        return false;
      }
      return true;
    }
    this.router.navigate(['/login']);
    return false;
  }
  
  //metodo o funcion, que analiza si el token esta expirado
  isTokenExpired(): boolean {
    let token = this.authService.token;
    let payload = this.authService.obtenerDatosToken(token);
    let now = new Date().getTime() / 1000;

    if(payload.exp < now) {
      return true;
    }
    return false;
  }
}
