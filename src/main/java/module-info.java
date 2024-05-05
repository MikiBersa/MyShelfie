open module proj.ingsw.hackapo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires json.simple;
    requires com.google.gson;

    exports it.polimi.ingsw;
    exports it.polimi.ingsw.server;
    exports it.polimi.ingsw.server.json;
    exports it.polimi.ingsw.client;
    exports it.polimi.ingsw.client.network;
    exports it.polimi.ingsw.client.visitor;
    exports it.polimi.ingsw.client.view.gui;
    exports it.polimi.ingsw.utils;
    exports it.polimi.ingsw.server.model;
    exports it.polimi.ingsw.server.model.common_objective_cards;
    exports it.polimi.ingsw.server.model.cards;
    exports it.polimi.ingsw.server.network;
    exports it.polimi.ingsw.server.network.Observer.oberserverIn;
    exports it.polimi.ingsw.server.network.Observer.oberserverOut;
    exports it.polimi.ingsw.server.network.Message;
    exports it.polimi.ingsw.server.network.Message.output;
    exports it.polimi.ingsw.server.network.Message.input;
    exports it.polimi.ingsw.client.view.gui.Scene;
    exports it.polimi.ingsw.client.view;
    exports it.polimi.ingsw.utils.store;
    exports it.polimi.ingsw.client.controller;
    exports it.polimi.ingsw.client.model;
}