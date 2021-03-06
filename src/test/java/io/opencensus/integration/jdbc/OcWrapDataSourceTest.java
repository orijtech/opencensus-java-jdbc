// Copyright 2019, OpenCensus Authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package io.orijtech.integrations.ocjdbc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.orijtech.integrations.ocjdbc.Observability.TraceOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.EnumSet;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OcWrapDataSourceTest {
  private static final EnumSet<TraceOption> OPTIONS = EnumSet.noneOf(TraceOption.class);

  @Mock private DataSource delegate;
  @Mock private Connection connection;

  private DataSource dataSource;

  @Before
  public void setUp() throws SQLException {
    dataSource = OcWrapDataSource.wrap(delegate, OPTIONS);
  }

  @Test
  public void testGetConnection() throws SQLException {
    when(delegate.getConnection()).thenReturn(connection);
    final Connection conn = dataSource.getConnection();
    conn.close();
    verify(connection).close();
  }

  @Test
  public void testGetConnectionUserPass() throws SQLException {
    when(delegate.getConnection("user", "pass")).thenReturn(connection);
    final Connection conn = dataSource.getConnection("user", "pass");
    conn.close();
    verify(connection).close();
  }
}
