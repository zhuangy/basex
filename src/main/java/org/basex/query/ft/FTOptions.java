package org.basex.query.ft;

import org.basex.query.*;
import org.basex.query.iter.*;
import org.basex.query.value.node.*;
import org.basex.util.*;
import org.basex.util.ft.*;

/**
 * FTOptions expression.
 *
 * @author BaseX Team 2005-12, BSD License
 * @author Christian Gruen
 */
public final class FTOptions extends FTExpr {
  /** FTOptions. */
  private final FTOpt opt;

  /**
   * Constructor.
   * @param ii input info
   * @param e expression
   * @param o ft options
   */
  public FTOptions(final InputInfo ii, final FTExpr e, final FTOpt o) {
    super(ii, e);
    opt = o;
  }

  @Override
  public FTExpr compile(final QueryContext ctx) throws QueryException {
    final FTOpt tmp = ctx.ftOpt();
    ctx.ftOpt(opt.copy(tmp));
    if(opt.sw != null && ctx.value != null && ctx.value.data() != null)
      opt.sw.comp(ctx.value.data());
    expr[0] = expr[0].compile(ctx);
    ctx.ftOpt(tmp);
    return expr[0];
  }

  @Override
  public void plan(final FElem plan) {
    addPlan(plan, planElem(), opt, expr[0]);
  }

  @Override
  public String toString() {
    return expr[0].toString() + opt;
  }

  @Override
  public FTNode item(final QueryContext ctx, final InputInfo ii) {
    // shouldn't be called, as compile returns argument
    throw Util.notexpected();
  }

  @Override
  public FTIter iter(final QueryContext ctx) {
    // shouldn't be called, as compile returns argument
    throw Util.notexpected();
  }
}
