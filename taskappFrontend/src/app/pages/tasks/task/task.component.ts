import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from 'src/app/services/api.service';
import Swal from 'sweetalert2';
import { Task } from './task.model';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {
  
  public task: Task = new Task();
  public taskPut: FormGroup;
  public Toast = Swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000,
    timerProgressBar: true,
    didOpen: (toast) => {
      toast.addEventListener('mouseenter', Swal.stopTimer);
      toast.addEventListener('mouseleave', Swal.resumeTimer);
    }
  });
  constructor(private routeActive: ActivatedRoute,
    private apiService: ApiService,
    public router: Router,
    public fb: FormBuilder) {
      this.taskPut = this.fb.group({
        nameTask: ['', [Validators.required]],
        task: ['', Validators.required]
      });
    }
    

  ngOnInit(): void {
    let id = this.routeActive.snapshot.params.idTask;
    this.getTask(id);
  }

  getTask(id: number) {
    this.apiService.getTask(id).subscribe(
      (resp: any) => {
        this.task = resp.task;
      },
      (err: HttpErrorResponse) => {
        if(err.error instanceof Error) {
          console.log('Client-side error');
        } else {
          console.error(err);
        }
    });
  }

  onSubmit(): any {
    let nameTask = this.taskPut.value.nameTask;
    let task = this.taskPut.value.task;
    if(nameTask == '' && task == ''){
      return this.Toast.fire({
        icon: 'error',
        title: 'No hay valores nuevos!',
        text: 'No has modificado ningun campo de la tarea'
      });
    } 

    if (nameTask != '' && task == '') {
      this.task.nameTask = nameTask;
    } else if (task != '' && nameTask == '') {
      this.task.task = task;
    } else if (nameTask != '' && task != '') {
      this.task.nameTask = nameTask;
      this.task.task = task;
    }

    this.taskPutMethod(this.task.id, this.task);
    
  }

  taskPutMethod(id: number, task: Task) {
    this.apiService.putTask(id, task).subscribe(
      () => {
        this.router.navigate(['/home']);
        this.Toast.fire({
          icon: 'success',
          title: 'Modificación',
          text: 'Has modificado una tarea!'
        })
      }, 
      (err: HttpErrorResponse) => {
        console.error(err);
      }
    )
  }

}
