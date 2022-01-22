import {Injectable} from '@angular/core';
import {Signalement} from "../models/Signalement";
import {HttpClient} from "@angular/common/http";
import {HOST} from "./constants";

@Injectable({
  providedIn: 'root'
})
export class SignalementService {

  idBricoleur: number|undefined;
  SERVER_ADRESSE = HOST;

  constructor(private http: HttpClient) {
  }

  addSignalement(signalement: Signalement) {
    return this.http.post<Signalement>(this.SERVER_ADRESSE + "/signalement", signalement);
  }

  getSignalementClient(annonceid: number | undefined) {
    return this.http.get<Signalement>(this.SERVER_ADRESSE + "/signalement/client/" + annonceid);
  }

  getSignalementPro(annonceid: number | undefined) {
    return this.http.get<Signalement>(this.SERVER_ADRESSE + "/signalement/professionnel/" + annonceid);
  }


  getSignalements(){
    return this.http.get<Signalement[]>(this.SERVER_ADRESSE + "/signalements");
  }
}
