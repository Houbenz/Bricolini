import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {HOST} from "./constants";
import {Notification} from "../models/Notification";

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private http: HttpClient) {
  }

  getNotifications() {
    return this.http.get<Notification[]>(HOST + "/notifications");
  }

  viewNotification(id: number) {
    return this.http.post(HOST + "/notifications/" + id, {});
  }
}
