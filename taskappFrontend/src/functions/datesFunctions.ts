import { Injectable } from "@angular/core";
import * as moment from "moment";

@Injectable({
  providedIn: 'root'
})
export class DatesFunctions {

  
  public formatDate(date: any) {
    let formatDate = (moment(date)).format('DD-MM-YYYY');
    return formatDate;
  }
}