/*
 * portablegetch.h
 *  http://stackoverflow.com/questions/6856635/hide-password-input-on-terminal
 *  http://www.cplusplus.com/forum/articles/10627/
 */

#ifndef PGETCH
#define PGETCH
#ifdef __unix__
#include <termios.h>
#include <unistd.h>
#include <stdio.h>
#include <cstdlib>

static struct termios n_term;
static struct termios o_term;

static int cbreak(int fd)
{
   if((tcgetattr(fd, &o_term)) == -1)
      return -1;
   n_term = o_term;
   n_term.c_lflag = n_term.c_lflag & ~(ECHO|ICANON);
   n_term.c_cc[VMIN] = 1;
   n_term.c_cc[VTIME]= 0;
   if((tcsetattr(fd, TCSAFLUSH, &n_term)) == -1)
      return -1;
   return 1;
}

static int getch()
{
   int cinput;

   if(cbreak(STDIN_FILENO) == -1) {
      fprintf(stderr, "cbreak failure, exiting \n");
      exit(EXIT_FAILURE);

   }
   cinput = getchar();
   tcsetattr(STDIN_FILENO, TCSANOW, &o_term);

   return cinput;
}

#elif _MSC_VER  || __WIN32__ || __MS_DOS__
  #include <conio.h>
#endif
#endif

