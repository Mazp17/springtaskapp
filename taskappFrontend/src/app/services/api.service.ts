import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Tasks } from '../interfaces/tasks.interface';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  _urlBase = 'http://localhost:8080/api/';

  
  constructor(private http: HttpClient,
    public authService: AuthService) {}

  getTasks() {
  return this.http.get(this._urlBase + 'task').pipe(
    catchError(e => {
      return throwError(e);
    })
  );
  }

  saveTask(task: Tasks) {
    return this.http.post(this._urlBase + 'task', task)
  }

  registerUser(user: any){
    return this.http.post(this._urlBase + 'user/register/1', user);
  }

  getTask(id: number) {
    return this.http.get(this._urlBase + 'task/' + id);
  }

  completedTask(id: number) {
    return this.http.put(this._urlBase + 'task/completed/' + id, null);
  }

  deletedTask(id: number) {
    return this.http.put(this._urlBase + 'task/deleted/' + id, null);
  }

  
  /*
  private IsNotAuthorization(e: any): boolean { 
    if(e.status==401) {
      if(this.authService.isAuthenticated()) {
        this.authService.logout();
      } 
      this.router.navigate(["/login"]);
      return true;
    } else {
      return false;
    }
  }*/

  putTask(id: number, task: Tasks) {
    return this.http.put(this._urlBase + 'task/update/' + id, task)
  }
}


