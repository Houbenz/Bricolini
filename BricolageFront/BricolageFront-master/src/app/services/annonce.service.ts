import {Injectable} from '@angular/core';
import {Domaine} from "../models/Domaine";
import {HttpClient} from "@angular/common/http";
import {Annonce} from "../models/Annonce";
import {HOST} from "./constants";

@Injectable({
  providedIn: 'root'
})
export class AnnonceService {
  SERVER_ADRESSE = HOST;
  isLoading = false;

  constructor(private http: HttpClient) {
  }

  getDomaines() {
    return this.http.get<Domaine[]>(this.SERVER_ADRESSE + "/domaines");
  }

  getAnnonces() {
    return this.http.get<Annonce[]>(this.SERVER_ADRESSE + "/annonces");
  }

  getAnnoncesByClient() {
    return this.http.get<Annonce[]>(this.SERVER_ADRESSE + "/clients/" + localStorage.getItem("currentUserId") + "/annonces");
  }

  getAnnoncesByBricoleur() {
    return this.http.get<Annonce[]>(this.SERVER_ADRESSE + "/bricoleurs/" + localStorage.getItem("currentUserId") + "/annonces");
  }

  deleteAnnonce(id: number | undefined) {
    return this.http.delete(this.SERVER_ADRESSE + "/annonces/" + id)
  }

  getAnnonce(id: number | undefined) {
    return this.http.get<Annonce>(this.SERVER_ADRESSE + "/annonces/" + id);
  }

  addAnnonce(annonce: FormData) {
    return this.http.post<Annonce>(this.SERVER_ADRESSE + "/annonces", annonce);
  }

  updateAnnonce(annonce: FormData) {
    return this.http.put<Annonce>(this.SERVER_ADRESSE + "/annonces", annonce);
  }

}
