<%@include file="layout/header.jspf" %>
<c:set var="MULTIPLIER_PRICE_FOR_BUSINESS_TICKETS" value="${multiplier * 1.4}"/>
<c:set var="MULTIPLIER" value="${multiplier}"/>
<title><fmt:message key="title.index" bundle="${lang}"/></title>
</head>

<body>
<div class="container">
    <div class="search tab-content">
        <form action="/flights" method="post" class="container-fluid colorlib-form">
            <div class="row" align="right">
                <div class="col-sm-3">
                    <div class="form-group">
                        <label><fmt:message key="from" bundle="${lang}"/></label>
                        <div class="form-field">
                            <select name="flight_from" size="1" class="form-control">
                                <c:forEach var="flight" items="${airports}">
                                    <c:choose>
                                        <c:when test="${flight.id == from}">
                                            <option selected
                                                    value="${flight.id}">${flight.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${flight.id}">${flight.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label><fmt:message key="to" bundle="${lang}"/></label>
                        <div class="form-field">
                            <select name="flight_to" size="1" class="form-control">
                                <c:forEach var="flight" items="${airports}">
                                    <c:choose>
                                        <c:when test="${flight.id == to}">
                                            <option selected
                                                    value="${flight.id}">${flight.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${flight.id}">${flight.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <div class="form-group">
                        <label><fmt:message key="departure" bundle="${lang}"/></label>
                        <div class="form-field">
                            <input type="date" class="form-control" name="date"
                                   value=${date.equals("") ? currentDate : date}>
                        </div>
                    </div>
                </div>
                <div class="col-sm-3 m-btn">
                    <input type="submit" value="<fmt:message key="find" bundle="${lang}"/>"
                           class="btn btn-primary btn-block">
                </div>
            </div>
        </form>
    </div>


    <c:if test="${error != null}">
        <label class="error"><fmt:message key="${error}" bundle="${lang}"/>!</label>
    </c:if>

    <c:if test="${not empty flights && flights.size() > 0}">
        <form action="/ticket" method="post">
            <label><fmt:message key="flight.label.available_flights" bundle="${lang}"/>:</label>
            <div class="form-group col-sm-12">
                <c:forEach var="flight" items="${flights}">
                    <div class="col-sm-4">
                        <div class="ticket">
                            <input name="selected_flight" type="radio"
                                   value="${flight.id}"/>${flight} <br>
                            <fmt:parseDate value="${ flight.departureTime }"
                                           pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDateTime" type="both"/>
                            <label><fmt:message key="ticket.departure.time" bundle="${lang}"/>:
                                <fmt:formatDate
                                        pattern="yyyy-MM-dd HH:mm"
                                        value="${ parsedDateTime }"/></label><br>
                            <fmt:parseDate value="${ flight.arrivalTime }"
                                           pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDateTime" type="both"/>
                            <label><fmt:message key="ticket.arrival.time" bundle="${lang}"/>:
                                <fmt:formatDate
                                        pattern="yyyy-MM-dd HH:mm"
                                        value="${ parsedDateTime }"/></label><br>

                            <label><fmt:message key="flight.label.economy_class" bundle="${lang}"/>:
                                <fmt:formatNumber
                                        value="${(flight.baseTicketPrice * MULTIPLIER)/100}"
                                        minFractionDigits="2" maxFractionDigits="2"/>
                                &#8364;</label><br>
                            <label><fmt:message key="flight.label.free_seats"
                                                bundle="${lang}"/>: ${flight.freeSeatEconomy}</label><br>
                            <label><fmt:message key="flight.label.business_class" bundle="${lang}"/>:
                                <fmt:formatNumber
                                        value="${(flight.baseTicketPrice * MULTIPLIER_PRICE_FOR_BUSINESS_TICKETS)/100}"
                                        minFractionDigits="2" maxFractionDigits="2"/>
                                &#8364;</label><br>
                            <label><fmt:message key="flight.label.free_seats"
                                                bundle="${lang}"/>: ${flight.freeSeatBusiness}</label><br>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <br>
            <div class="col-sm-12">
                <c:choose>
                    <c:when test="${account == null}">
                        <label class="error"><fmt:message key="registrate.warning"
                                                          bundle="${lang}"/>.</label>
                    </c:when>
                    <c:otherwise>
                        <div class="s-btn col-sm-9"></div>
                        <div class="s-btn col-sm-3">
                            <input type="submit"
                                   value="<fmt:message key="select" bundle="${lang}"/>"
                                   class="btn btn-primary btn-block">
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </form>
        <%--<label><c:if test="${error != null}"> ${error}</c:if></label>--%>
    </c:if>
    <hr/>
</div>
<%@include file="layout/footer.jspf" %>

</body>
</html>