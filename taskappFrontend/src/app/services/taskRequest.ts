import { Injectable } from "@angular/core";
import Swal from "sweetalert2";
import { ApiService } from "./api.service";

@Injectable({
  providedIn: 'root'
})
export class TaskRequest {

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
  
  constructor(private apiService: ApiService) { }
  
  deletedTask1(id: any) {
    return this.apiService.deletedTask(id).subscribe(
      () => {
        this.Toast.fire({
          icon: 'warning',
          title: 'Eliminado!',
          text: 'Has eliminado una tarea! ahora puedes encontrarla en la papelera'
        });
        //callback();
      },
      (error) => {
        console.error(error);
      });
  }

  deletedTask(id: any) {
    return this.apiService.deletedTask(id);
  }
}