import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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

  @ViewChild('addTask') modal!: ElementRef;


  constructor(private apiService: ApiService, 
    private modalService:NgbModal,
    public authService: AuthService,
    private router: Router) {}

   ngOnInit(): void {
    this.apiService
    .getTasks(this.authService.usuario)
    .subscribe((resp: any) => {
      console.log(resp);
    this.tasks = resp.tasks;
  },
  (err: HttpErrorResponse) => {
    if(err.error instanceof Error) {
      console.log('Client-side error');

    } else {
      console.error(err);
      this.errorMio = "El servidor no responde, puede ser que este apagado. Comuniquese con el administrador del servicio.";
    }
  });
  console.log(localStorage.getItem('usuario'));
  }

  


   openModal(modal: ElementRef){
     this.modalService.open(modal);
   }

   closeModal() {
      this.modalService.dismissAll();
   }
   
}

