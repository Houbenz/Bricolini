import {Component, OnInit} from '@angular/core';
import {DomaineService} from '../services/domaine.service';
import {Domaine} from '../models/Domaine';

@Component({
  selector: 'app-add-domaine',
  templateUrl: './add-domaine.component.html',
  styleUrls: ['./add-domaine.component.css']
})
export class AddDomaineComponent implements OnInit {

  constructor(private domaineService: DomaineService) {
  }

  responseMessage: string | undefined;


  myDomaine: Domaine = {
    description: "", domaine: "",
    id: -2
  };

  formErrors: string[] = [];

  ngOnInit(): void {

  }


  onSendDomaine() {


    this.addDomaine(this.myDomaine);

  }

  addDomaine(domaine: Domaine) {

    this.domaineService.addDomaine(domaine).subscribe(response => {

      this.responseMessage = response.response;

    });

  }

  newDomaine() {
    this.myDomaine = {
      description: "", domaine: "", id: -2
    };
  }
}

