import { ZzpMatcherPage } from './app.po';

describe('zzp-matcher App', () => {
  let page: ZzpMatcherPage;

  beforeEach(() => {
    page = new ZzpMatcherPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
