import {Injectable} from '@angular/core';
import {Avis} from "../models/Avis";
import {HttpClient} from "@angular/common/http";
import {HOST} from "./constants";

@Injectable({
  providedIn: 'root'
})
export class AvisService {
  SERVER_ADRESSE = HOST;

  constructor(private http: HttpClient) {
  }

  addAvis(avis: Avis) {
    return this.http.post<Avis>(this.SERVER_ADRESSE + "/avis/publier", avis);
  }

  getAvisClient(id: number | undefined) {
    return this.http.get<Avis>(this.SERVER_ADRESSE + "/avis/client/" + id);
  }

  getAvisPro(id: number | undefined) {
    return this.http.get<Avis>(this.SERVER_ADRESSE + "/avis/professionnel/" + id);
  }

}
