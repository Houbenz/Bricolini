import {User} from "./User";
import {Annonce} from "./Annonce";

export interface Notification {
  id: number;
  annonce: Annonce;
  user: User;
}
