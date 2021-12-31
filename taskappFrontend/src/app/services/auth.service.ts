import { HttpClient, HttpHeaders, JsonpClientBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../pages/user/user';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _usuario!: User;
  private _token!: string;
  private sessionUser = sessionStorage.getItem('user');
  private sessionToken = sessionStorage.getItem('token');

  constructor(private http: HttpClient) { }


  public get usuario(): User {
    if(this._usuario != null) {
      return this._usuario;
    } else if (this._usuario == null && this.sessionUser != null) {
      //let user = localStorage.getItem('user') as User;
      this._usuario = JSON.parse(this.sessionUser);
      return this._usuario;
    } 

    return new User();
  }

  public get token(): any {
    if(this._token != null) {
      return this._token;
    } else if (this._token == null && this.sessionToken != null) {
      this._token = this.sessionToken;
      return this._token;
    } 
    return null;
  }

  login(user: User): Observable<any> {
    const urlEndPoint = 'http://localhost:8080/oauth/token';

    const credentials = btoa('angularapp' + ':' + '12345');

    const httpHeaders = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded', 
      'Authorization': 'Basic ' + credentials
    });

    let params = new URLSearchParams();
    params.set('username', user.email);
    params.set('password', user.password);
    params.set('grant_type', 'password');
    console.log(params.toString());
    return this.http.post<any>(
      urlEndPoint, params.toString(), 
      {
        headers: httpHeaders
      }
    );
  }

  guardarUsuario(accessToken: string): void {
    let payload = this.obtenerDatosToken(accessToken);
    this._usuario = new User();
    this._usuario.username = payload.username;
    this._usuario.email = payload.email;
    this._usuario.password = payload.password;

    sessionStorage.setItem('user', JSON.stringify(this._usuario));
    
  }

  guardarToken(accessToken: string): void {
    this._token = accessToken;

    sessionStorage.setItem('token', accessToken);
  }

  obtenerDatosToken(accessToken:string): any {
    if(accessToken != null && accessToken != '') {
      return JSON.parse(atob(accessToken.split(".")[1]));
    }
    return null;
  }

  isAuthenticated(): boolean {
    let payload = this.obtenerDatosToken(this.token);
    if(payload != null && payload.user_name && payload.user_name.length>0) {
      return true;
    }
    return false;
  }

  logout(): void {
    this._token = '';
    this._usuario = new User();
    sessionStorage.clear();
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('usuario');
  }
}
