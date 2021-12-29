import { HttpClient, HttpHeaders, JsonpClientBackend } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../pages/user/user';
import jwt_decode from "jwt-decode";

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
      this._token = this.sessionToken || '';
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
    params.set('username', user.username);
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
    this._usuario.username = payload.user_name;
    this._usuario.email = payload.email;

    sessionStorage.setItem('user', JSON.stringify(this._usuario));
    
  }

  guardarToken(accessToken: string): void {
    this._token = accessToken;

    sessionStorage.setItem('token', accessToken);
  }

  obtenerDatosToken(accessToken:string): any {
    if(accessToken != null) {
      let tokenSplit = accessToken.split(".")[1];
      //return jwt_decode(tokenSplit);
      //return JSON.parse(atob(accessToken.split(".")[1]));
      return JSON.parse(decodeURIComponent(escape(window.atob((tokenSplit).replace("-", "+").replace("_", "/")))))
      //return JSON.parse(this.b64_to_utf8(tokenSplit));
      //return atob(accessToken.split(".")[1]);
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

   b64_to_utf8( str: string ) {
    str = str.replace(/\s/g, '');    
    return decodeURIComponent(escape(window.atob( str )));
}

  b64DecodeUnicode(str: string): string {
    return decodeURIComponent(Array.prototype.map.call(atob(str), function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2)
    }).join(''))
}

   fromBinary(encoded: string) {
    let binary = atob(encoded)
    const bytes = new Uint8Array(binary.length);
    for (let i = 0; i < bytes.length; i++) {
      bytes[i] = binary.charCodeAt(i);
    }
    return String.fromCharCode(...new Uint16Array(bytes.buffer));
  }

    /**
   * 
   * @param utftext 
   * 
   * string =  cigueÃ±a nÃºmeros
   * La funcion decode(string) da como resultado cigueña números
   * 
   * 
   * 
   */
     decode(utftext: string) {
      var string = "";
      var i = 0;
      var c = 0;
      var c1 = 0;
      var c2 = 0;
      var c3 = 0;
  
      while (i < utftext.length) {
  
        c = utftext.charCodeAt(i);
  
        if (c < 128) {
          string += String.fromCharCode(c);
          i++;
        }
        else if ((c > 191) && (c < 224)) {
          c2 = utftext.charCodeAt(i + 1);
          string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
          i += 2;
        }
        else {
          c2 = utftext.charCodeAt(i + 1);
          c3 = utftext.charCodeAt(i + 2);
          string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
          i += 3;
        }
  
      }
  
      return string;
    }
}
