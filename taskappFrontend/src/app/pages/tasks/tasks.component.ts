import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ApiService } from 'src/app/services/api.service';
import { AuthService } from 'src/app/services/auth.service';
//TODO: Implementar metodos para registro de nuevas tareas.
@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent{

  title = 'task';
  public tasks: Array<any> = []; 
  public errorMio: any;


  constructor(private apiService: ApiService, 
    private modal:NgbModal,
    public authService: AuthService,
    private router: Router) {
      
   }

   ngOnInit(): void {
    this.apiService
    .getTasks()
    .subscribe((resp: any) => {
    this.tasks = resp;  
  },
  (err: HttpErrorResponse) => {
    if(err.error instanceof Error) {
      console.log('Client-side error');

    } else {
      this.errorMio = "El servidor no responde, puede ser que este apagado. Comuniquese con el administrador del servicio.";
    }
  }
  );
  console.log(localStorage.getItem('usuario'));
  }


   OpenCenter(contenido: any){
     this.modal.open(contenido);
   }

   
}

