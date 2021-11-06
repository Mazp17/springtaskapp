import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { TasksComponent } from './pages/tasks/tasks.component';
import { UserComponent } from './pages/user/user.component';


const routes: Routes = [
  {path: 'home', component: TasksComponent},
  {path: 'user', component: UserComponent},
  {path: '**', pathMatch: 'full', redirectTo: 'home'}

];

@NgModule({
  imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
