package nwawsoft.util;

public class Graph {
    private List nodeList;

    /**
     * Ein neuer Graph wird erzeugt.
     * Er enthaelt noch keine Knoten.
     */
    public Graph() {
        nodeList = new List();
    }

    /**
     * Die Anfrage liefert true, wenn der Graph keine Knoten
     * enthaelt, andernfalls liefert die Anfrage false.
     *
     * @return true, falls leer, sonst false
     */
    public boolean isEmpty() {
        return nodeList.isEmpty();
    }

    /**
     * Der Knoten pNode wird dem Graphen hinzugefuegt.
     * Falls bereits ein Knoten mit gleichem Namen im
     * Graphen existiert, wird dieser Knoten nicht eingefuegt.
     * Falls pNode null ist, veraendert sich der Graph nicht.
     *
     * @param pNode neuer Knoten
     */
    public void addNode(GraphNode pNode) {
        if (pNode != null && !this.hasNode(pNode.getName()))
            nodeList.append(pNode);
    }

    /**
     * Die Anfrage liefert true, wenn ein Knoten mit dem Namen
     * pName im Graphen existiert.
     * Sonst wird false zurueck gegeben.
     *
     * @param pNode Knotenbezeichnung
     * @return true, falls es Knoten gibt, sonst false
     */
    public boolean hasNode(String pNode) {
        boolean lGefunden = false;
        nodeList.toFirst();
        while (nodeList.hasAccess() && !lGefunden) {
            GraphNode lKnoten = (GraphNode) nodeList.getObject();
            lGefunden = lKnoten.getName().equals(pNode);
            nodeList.next();
        }
        return lGefunden;
    }

    /**
     * Die Anfrage liefert den Knoten mit dem Namen pName zurueck.
     * Falls es keinen Knoten mit dem Namen im Graphen gibt,
     * wird null zurueck gegeben.
     *
     * @param pName Knotenbezeichnung
     * @return Objekt der Klasse GraphNode
     */
    public GraphNode getNode(String pName) {
        GraphNode lNode0 = null;
        nodeList.toFirst();
        boolean lStop = false;
        while (nodeList.hasAccess() && !lStop) {
            GraphNode lNode = (GraphNode) nodeList.getObject();
            if (lNode.getName().equals(pName)) {
                lNode0 = lNode;
                lStop = true;
            }
            nodeList.next();
        }
        return lNode0;
    }

    /**
     * Falls pNode ein Knoten des Graphen ist, so werden er und alle
     * mit ihm verbundenen Kanten aus dem Graphen entfernt.
     * Sonst wird der Graph nicht veraendert.
     *
     * @param pNode Knoten
     */
    public void removeNode(GraphNode pNode) {
        if (pNode != null) {
            nodeList.toFirst();
            while (nodeList.hasAccess()) {
                GraphNode lNode = (GraphNode) nodeList.getObject();
                if (lNode.getName().equals(pNode.getName())) {
                    List lListe = this.getNeighbours(lNode);
                    lListe.toFirst();
                    while (lListe.hasAccess()) {
                        GraphNode lNode1 = (GraphNode) lListe.getObject();
                        this.removeEdge(lNode, lNode1);
                        lListe.next();
                    }
                    nodeList.remove();
                }
                nodeList.next();
            }
        }
    }

    /**
     * Falls eine Kante zwischen pNode1 und pNode2 noch nicht existiert,
     * werden die Knoten pNode1 und pNode2 durch eine Kante verbunden,
     * die das Gewicht pWeight hat. pNode1 ist also Nachbarknoten
     * von pNode2 und umgekehrt. Falls eine Kante zwischen pNode1 und pNode2
     * bereits existiert, erhaelt sie das Gewicht pWeight.
     * Falls einer der Knoten pNode1 oder pNode2 im Graphen nicht existiert oder null ist,
     * veraendert sich der Graph nicht.
     *
     * @param pNode1  Knoten
     * @param pNode2  Knoten
     * @param pWeight Kantengewicht
     */
    public void addEdge(GraphNode pNode1, GraphNode pNode2,
                        double pWeight) {
        if (pNode1 != null && pNode2 != null) {
            if (this.hasEdge(pNode1, pNode2))
                this.removeEdge(pNode1, pNode2);
            pNode1.addEdge_(pNode2, pWeight);
            //pNode2.addEdge_(pNode1,pWeight);
        }
    }

    /**
     * Die Anfrage liefert true, wenn ein Knoten mit
     * dem Namen pName im Graphen existiert.
     * Sonst wird false zurueck gegeben.
     *
     * @param pNode1 Knoten
     * @param pNode2 Knoten
     * @return true, falls Kante existiert, sonst false
     */
    public boolean hasEdge(GraphNode pNode1, GraphNode pNode2) {
        boolean result = false;
        List lNeighbours;
        GraphNode lNeighbour;
        if ((pNode1 != null) && (pNode2 != null)) {
            lNeighbours = pNode1.getNeighbours_();
            if (!lNeighbours.isEmpty()) {
                lNeighbours.toFirst();
                while (lNeighbours.hasAccess()) {
                    lNeighbour = (GraphNode) lNeighbours.getObject();
                    if (lNeighbour.getName().equals(pNode2.getName()))
                        result = true;
                    lNeighbours.next();
                }
            }
        }
        return result;
    }

    /**
     * Falls pNode1 und pNode2 nicht null sind und eine Kante zwischen
     * pNode1 und pNode2 existiert, wird die Kante geloescht. Sonst
     * bleibt der Graph unveraendert.
     *
     * @param pNode1 Knoten
     * @param pNode2 Knoten
     */
    public void removeEdge(GraphNode pNode1,
                           GraphNode pNode2) {
        if (pNode1 != null &&
                pNode2 != null &&
                this.hasEdge(pNode1, pNode2)) {
            pNode1.removeEdge_(pNode2);
            pNode2.removeEdge_(pNode1);
        }
    }

    /**
     * Die Anfrage liefert das Gewicht der Kante zwischen pNode1 und pNode2.
     * Falls die Kante nicht existiert, wird Double.NaN (not a number)
     * zurueck gegeben.
     *
     * @param pNode1 Knoten
     * @param pNode2 Knoten
     * @return Kantengewicht
     */
    public double getEdgeWeight(GraphNode pNode1, GraphNode pNode2) {
        return pNode1.getEdgeWeight_(pNode2);
    }

    /**
     * Unmarks all GraphNode objects in the Graph.
     */
    public void unmark() {
        if (!nodeList.isEmpty()) {
            nodeList.toFirst();
            while (nodeList.hasAccess()) {
                ((GraphNode) nodeList.getObject()).unmark();
                nodeList.next();
            }
        }
    }

    /**
     * Die Anfrage liefert den Wert true, wenn alle Knoten des Graphen
     * markiert sind, sonst liefert sie den Wert false.
     *
     * @return true, lass alle Knoten markiert, sonst false
     */
    public boolean allNodesMarked() {
        if (!nodeList.isEmpty()) {
            nodeList.toFirst();
            boolean lAllMarked = true;
            while (nodeList.hasAccess() &&
                    lAllMarked) {
                if (!((GraphNode) nodeList.getObject()).isMarked())
                    lAllMarked = false;
                nodeList.next();
            }
            return lAllMarked;
        } else return true;
    }

    /**
     * Die Anfrage liefert eine Liste, die alle Knoten des Graphen enthaelt.
     *
     * @return Knotenliste
     */
    public List getNodes() {
        List lList = new List();
        nodeList.toFirst();
        while (nodeList.hasAccess()) {
            GraphNode g = (GraphNode) nodeList.getObject();
            lList.append(g);
            nodeList.next();
        }
        return lList;
    }

    /**
     * Die Anfrage liefert eine Liste, die alle Nachbarknoten des
     * Knotens pNode enthaelt.
     *
     * @param pNode Knoten
     * @return Liste mit allen Nachbarknoten
     */
    public List getNeighbours(GraphNode pNode) {
        return pNode.getNeighbours_();
    }

    /**
     * Returns the sum of all edeWeights in the Graph.
     *
     * @return all edgeWeights in the Graph summed up.
     */
    public double edgeWeightSum() {
        double combinedWeight = 0;
        List allNodes = getNodes();
        allNodes.toFirst();
        while (allNodes.hasAccess()) {
            combinedWeight = combinedWeight + edgeWeight((GraphNode) allNodes.getObject());
            allNodes.next();
        }
        return combinedWeight;
    }

    /**
     * Returns the added edeWeights of all edges of the specified GraphNode object.
     *
     * @param pNode a GraphNode object.
     * @return the sum of all edeWeights of the GraphNode (and his neighbors).
     */
    public double edgeWeight(GraphNode pNode) {
        double nodeWeight = 0;
        List allNeighbours = pNode.getNeighbours_();
        allNeighbours.toFirst();
        while (allNeighbours.hasAccess()) {
            nodeWeight = nodeWeight + getEdgeWeight(pNode, (GraphNode) allNeighbours.getObject());
            allNeighbours.next();
        }
        return nodeWeight;
    }

    /**
     * Returns the GraphNode object with the highest added edgeWeights in the Graph.
     *
     * @return the GraphNode with the highest added edgeWeights.
     */
    public GraphNode highestWeight() {
        GraphNode heaviestNode;
        List allNodes = this.getNodes();
        allNodes.toFirst();
        heaviestNode = (GraphNode) allNodes.getObject();
        while (allNodes.hasAccess()) {
            if (edgeWeight(heaviestNode) < edgeWeight((GraphNode) allNodes.getObject())) {
                heaviestNode = (GraphNode) allNodes.getObject();
            }
            allNodes.next();
        }
        return heaviestNode;
    }
}