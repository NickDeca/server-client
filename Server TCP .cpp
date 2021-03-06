// Server TCP.cpp : Defines the entry point for the console application.
//

#define WIN32_LEAN_AND_MEAN

#include <SDKDDKVer.h>
#include <stdio.h>
#include <tchar.h>
#include<string>
#include<stdlib.h>
#include<iostream>
#include <ws2tcpip.h>
#include<winsock2.h>

#pragma comment(lib,"ws2_32.lib")

using namespace std;

#define BUFLEN 512
#define PORT "1234"

int main()
{
	WSADATA data;
	struct addrinfo *result;
	struct addrinfo hints;
	char recvbuf[BUFLEN];
	int recvbuflen = BUFLEN;


	if (WSAStartup(MAKEWORD(2, 2), &data) != 0)
	{
		cout << "Failed. Error Code : " << WSAGetLastError() << endl;
		WSACleanup();
		exit(EXIT_FAILURE);
	}

	ZeroMemory(&hints, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_protocol = IPPROTO_TCP;
	hints.ai_flags = AI_PASSIVE;
	
	int a = getaddrinfo(NULL, PORT, &hints, &result);
	if (getaddrinfo(NULL, PORT, &hints, &result) != 0) {
		cout << "getaddrinfo failed with error: " << WSAGetLastError();
		WSACleanup();
		exit(EXIT_FAILURE);
	}
	// we create a socket for connecting to server
	int ListenSocket = socket(result->ai_family, result->ai_socktype, result->ai_protocol);
	if (ListenSocket == SOCKET_ERROR) {
		cout << "Failed. Error Code : " << WSAGetLastError() << endl;
		WSACleanup();
		exit(EXIT_FAILURE);
	}
	// we bind the socket to an address
	if (bind(ListenSocket, result->ai_addr, (int)result->ai_addrlen) == SOCKET_ERROR) {
		cout << "bind failed with error:" << WSAGetLastError() << endl;
		WSACleanup();
		exit(EXIT_FAILURE);
	}

	//the somaxconn is used to limit the maximum number of connections in socket
	//and it listens to clients 
	if (listen(ListenSocket, SOMAXCONN) == SOCKET_ERROR) {
		cout << "listen failed with error:" << WSAGetLastError() << endl;
		closesocket(ListenSocket);
		WSACleanup();
		exit(EXIT_FAILURE);
	}
	//here it accepts a client socket
	int ClientSocket = accept(ListenSocket, NULL, NULL);
	if (ClientSocket == SOCKET_ERROR) {
		cout << "accept failed with error:" << WSAGetLastError() << endl;
		closesocket(ListenSocket);
		WSACleanup();
		exit(EXIT_FAILURE);
	}
	//after it accepted a client , the ClientSocket is used so we close the ListenSocket
	closesocket(ListenSocket);

	//receive from client
	int bytes = recv(ClientSocket, recvbuf, recvbuflen, 0);
	if (bytes > 0) {
		cout << "Bytes received: " << bytes;

		if (send(ClientSocket, recvbuf, bytes, 0) == SOCKET_ERROR) {
			cout << "send failed with error: " << WSAGetLastError() << endl;
			closesocket(ClientSocket);
			WSACleanup();
			exit(EXIT_FAILURE);
		}
	}
	else if (bytes == 0) {
		cout << "Connection closing...\n";
	}
	else {
		cout << "recv failed with error :" << WSAGetLastError() << endl;
		closesocket(ClientSocket);
		WSACleanup();
		exit(EXIT_FAILURE);
	}

	bytes = shutdown(ClientSocket,SD_SEND);

	closesocket(ClientSocket);
	WSACleanup();


    return 0;
}

