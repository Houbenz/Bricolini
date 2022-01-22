import {Domaine} from "./Domaine";

export class User {

  constructor(
    public id?: number,
    public nom?: string,
    public prenom?: string,
    public email?: string,
    public username?: string,
    public phone_number?: string,
    public password?: string,
    public adresse?: string,
    public role?: string,
    public domaine?: Domaine,
    public premium?: boolean,
    public repassword?: string,
    public rate?: number,
    public bloqued? :number
  ) {
  }
}
