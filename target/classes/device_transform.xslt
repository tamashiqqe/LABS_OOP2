<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Computer Components</h2>
                <table border="1">
                    <tr>
                        <th>Name</th>
                        <th>Origin</th>
                        <th>Price</th>
                        <th>Critical</th>
                        <th>Peripheral</th>
                        <th>Energy Consumption</th>
                        <th>Cooling</th>
                        <th>Group</th>
                        <th>Ports</th>
                    </tr>
                    <xsl:for-each select="Device/Component">
                        <tr>
                            <td><xsl:value-of select="@Name"/></td>
                            <td><xsl:value-of select="@Origin"/></td>
                            <td><xsl:value-of select="@Price"/></td>
                            <td><xsl:value-of select="@Critical"/></td>
                            <td><xsl:value-of select="Type/Peripheral"/></td>
                            <td><xsl:value-of select="Type/EnergyConsumption"/></td>
                            <td><xsl:value-of select="Type/Cooling"/></td>
                            <td><xsl:value-of select="Type/Group"/></td>
                            <td>
                                <xsl:for-each select="Type/Ports/Port">
                                    <xsl:value-of select="."/><xsl:text>, </xsl:text>
                                </xsl:for-each>
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
