import {User} from "./User";
import {Image} from "./Image";

export interface Annonce {
  id: number,
  adresse: string,
  budgetClient: number,
  date: string,
  description: string,
  details: string,
  idDomaine: number,
  idUser: number,
  titre: string,
  termine: boolean,
  image: Image,
  client: User
}
