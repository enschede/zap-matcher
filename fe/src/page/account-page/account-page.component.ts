import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ProfileService} from '../../service/index';

@Component({
  selector: 'app-account-page',
  templateUrl: './account-page.component.html',
  styleUrls: ['./account-page.component.css']
})
export class AccountPageComponent {
  username: string;

  constructor(private profileService: ProfileService,
              private router: Router) {
    this.profileService.getProfile().subscribe((json: any) => {
      console.log(json);
    }, (err: any) => {
      console.log(err);
      this.router.navigate(['']);
    }, () => {
      console.log('Closes');
    });
  }

}
