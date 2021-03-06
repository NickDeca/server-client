// Client TCP.cpp : Defines the entry point for the console application.
//


#include <stdio.h>
#include <tchar.h>
#include <SDKDDKVer.h>
#include <winsock2.h>
#include <stdio.h>
#include <string>
#include <iostream>
#include"Ws2tcpip.h"

#pragma comment(lib,"ws2_32.lib")

using namespace std;

#define BUFLEN 512
#define PORT "1234"
#define SERVER "127.0.0.1"

int main()
{
	WSADATA data;
	char toserv[BUFLEN];
	char recvbuf[BUFLEN];
	int recvbuflen = BUFLEN;
	struct addrinfo *result = NULL ,hints;


	if (WSAStartup(MAKEWORD(2, 2), &data) != 0) {
		cout << "Failed. Error Code :" << WSAGetLastError() << endl;
		WSACleanup();
		exit(EXIT_FAILURE);
	}

	ZeroMemory(&hints, sizeof(hints));
	hints.ai_family = AF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_protocol = IPPROTO_TCP;

	int a = getaddrinfo(SERVER, PORT, &hints, &result);

	if (a != 0) {
		cout << "getaddrinfo failed. Error Code :" << WSAGetLastError() << endl;
		WSACleanup();
		exit(EXIT_FAILURE);
	}

	//create a socket for connecting to server
	int ConnectSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
	if (ConnectSocket == SOCKET_ERROR) {
		cout << "socket() failed with error code : " << WSAGetLastError() << endl;
		
		WSACleanup();
		exit(EXIT_FAILURE);

	}

	if (connect(ConnectSocket, result->ai_addr, (int)result->ai_addrlen) == SOCKET_ERROR) {
		closesocket(ConnectSocket);
		cout << "connection failed with error code : " << WSAGetLastError() << endl;
		WSACleanup();
		exit(EXIT_FAILURE);
	}
	
	cout << "Give your message ";
	cin.getline(toserv, BUFLEN);
	
	if (send(ConnectSocket, toserv, (int)strlen(toserv), 0) == SOCKET_ERROR) {
		cout << "send() failed with error code : " << WSAGetLastError() << endl;
		closesocket(ConnectSocket);
		WSACleanup();
		exit(EXIT_FAILURE);
	}
	memset(recvbuf, '\0', BUFLEN);
	//recieveing the echo from server
	int bytes = recv(ConnectSocket, recvbuf, recvbuflen, 0);
	if (bytes > 0) {
		cout << "Bytes received: " << bytes << endl;
		cout << "Data recieved : " << recvbuf << endl;
	}
	else {
		cout << "recv() failed with error code : " << WSAGetLastError() << endl;
	}
	
	if (shutdown(ConnectSocket, SD_SEND) == SOCKET_ERROR) {
		cout << "shutdown failed with error code : " << WSAGetLastError() << endl;
		closesocket(ConnectSocket);
		WSACleanup();
		exit(EXIT_FAILURE);

	}
	system("pause");
	closesocket(ConnectSocket);
	WSACleanup();

    return 0;
}

